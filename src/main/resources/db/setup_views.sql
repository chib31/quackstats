CREATE OR REPLACE VIEW batting_stats AS (
  SELECT
    b.id,
    s.team_id AS team_id,
    p.scorecard_name AS player_name,
    b.runs,
    b.deliveries,
    b.fours,
    b.sixes,
    b.batting_conclusion,
    b.position,
    s.date AS fixture_date,
    EXTRACT(year from s.date) AS season,
    o.name AS opposition,
    s.result_type,
    i.runs AS team_total,
    s.match_type,
    s.over_length
  FROM bat b
  JOIN innings i on b.innings_id = i.id
  JOIN squad_member sm on b.squad_member_id = sm.id
  JOIN player p on sm.player_id = p.id
  JOIN scorecard s on i.scorecard_id = s.id
  JOIN team o on s.opponent_id = o.id
);

CREATE OR REPLACE VIEW bowling_stats AS (
  WITH wicket_agg AS (
    SELECT
      w.bowler_id AS bowler_sm_id,
      SUM(w.batting_position) AS sum_victim_position,
      SUM(w.batter_runs) AS sum_victim_runs,
      COUNT(*) FILTER (WHERE w.batting_conclusion = 'BOWLED') AS wickets_bowled,
      COUNT(*) FILTER (WHERE w.batting_conclusion = 'LBW') AS wickets_lbw,
      COUNT(*) FILTER (WHERE w.batting_conclusion = 'CAUGHT') AS wickets_caught,
      COUNT(*) FILTER (WHERE w.batting_conclusion = 'STUMPED') AS wickets_stumped
    FROM wicket w
    GROUP BY w.bowler_id
  )
  SELECT
    b.id,
    s.team_id AS team_id,
    p.scorecard_name AS player_name,
    b.bowler_number,
    b.deliveries,
    b.runs,
    b.wickets,
    b.maidens,
    b.no_balls,
    b.wides,
    b.hat_tricks,
    s.date AS fixture_date,
    EXTRACT(year from s.date) AS season,
    o.name AS opposition,
    s.result_type,
    i.runs AS team_total,
    s.match_type,
    s.over_length,
    w.sum_victim_position,
    w.sum_victim_runs,
    w.wickets_bowled,
    w.wickets_lbw,
    w.wickets_caught,
    w.wickets_stumped
  FROM bowl b
  JOIN innings i on b.innings_id = i.id
  JOIN squad_member sm on b.squad_member_id = sm.id
  JOIN wicket_agg w ON w.bowler_sm_id = sm.id
  JOIN player p on sm.player_id = p.id
  JOIN scorecard s on i.scorecard_id = s.id
  JOIN team o on s.opponent_id = o.id
);

CREATE OR REPLACE VIEW fielding_stats AS (
  WITH wicket_types AS (
    SELECT w.fielder_id, w.innings_id, w.batting_conclusion AS type, COUNT(*) total
    FROM wicket w
    WHERE w.fielder_id IS NOT NULL
    GROUP BY w.innings_id, fielder_id, w.batting_conclusion
  ), wicket_totals AS (
    SELECT
      sm.id AS id,
      COALESCE(sm.captain, false) AS captain,
      COALESCE(sm.keeper, false) AS keeper,
      COALESCE((SELECT total FROM wicket_types WHERE fielder_id = sm.id AND type = 'CAUGHT'), 0) AS catches,
      COALESCE((SELECT total FROM wicket_types WHERE fielder_id = sm.id AND type = 'RUN_OUT'), 0) AS run_outs,
      COALESCE((SELECT total FROM wicket_types WHERE fielder_id = sm.id AND type = 'STUMPED'), 0) AS stumpings,
      COALESCE((SELECT COUNT(*) FROM wicket w WHERE fielder_id = sm.id), 0) wickets
    FROM squad_member sm
  )
  SELECT
    sm.id AS id,
    s.team_id AS team_id,
    p.scorecard_name AS player_name,
    wt.captain,
    wt.keeper,
    wt.catches,
    wt.run_outs,
    wt.stumpings,
    wt.wickets,
    s.date AS fixture_date,
    EXTRACT(year from s.date) AS season,
    t.name AS opposition,
    s.result_type,
    s.match_type,
    s.over_length
  FROM squad_member sm
  JOIN wicket_totals wt ON sm.id = wt.id
  JOIN player p ON sm.player_id = p.id
  JOIN scorecard s on sm.scorecard_id = s.id
  JOIN team t ON s.opponent_id = t.id
);