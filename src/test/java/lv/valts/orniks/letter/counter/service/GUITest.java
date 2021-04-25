package lv.valts.orniks.letter.counter.service;

import lv.valts.orniks.letter.counter.ui.GUI;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;

public class GUITest {


    @Test
    void testTemplateFileExistsInLocation(){
        URL resource = GUI.class.getResource("/main.fxml");
        assertNotNull(resource, "Could not get recourse location");
        assertNotNull(resource.getFile(),"Could not init a file");
    }
}
