package co.uk.cbradbury.quackstats.controller;

import co.uk.cbradbury.quackstats.json.ScorecardJson;
import co.uk.cbradbury.quackstats.model.entity.Scorecard;
import co.uk.cbradbury.quackstats.service.BackupService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Optional;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BackupController.class)
class BackupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BackupService backupService;

    @Test
    void createFullScorecardFromBackup() throws Exception {
        var jsonScorecard = fetchTextResource("full_scorecard.json");

        mockMvc.perform(put("/scorecard-backup/create-new-from-backup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonScorecard))
                .andExpect(status().isNoContent());
    }

    @Test
    void getScorecardJson() throws Exception {
        var testScorecard = new Scorecard();
        UUID id = testScorecard.getId();

        when(backupService.findScorecardById(id)).thenReturn(Optional.of(testScorecard));

        when(backupService.fetchScorecardJson(testScorecard)).thenReturn(new ScorecardJson());

        mockMvc.perform(get("/scorecard-backup/retrieve-backup/1"))
                .andExpect(status().isOk());

    }

    private String fetchTextResource(String path) throws IOException {
        var resourceLoader = new DefaultResourceLoader();
        var resource = resourceLoader.getResource(String.format("classpath:%s", path));

        Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8);

        return IOUtils.toString(reader);
    }
}