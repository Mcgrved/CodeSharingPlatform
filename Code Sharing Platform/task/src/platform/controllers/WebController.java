package platform.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import platform.Code;
import platform.CodeService;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
public class WebController {

    @Autowired
    private CodeService codeService;

    @GetMapping("/code/{id}")
    public Object getCodeSnippet(@PathVariable UUID id, Model model) {
        Code code = codeService.getCodeSnippet(id);
        model.addAttribute("code_list", code);

        if (Objects.equals(code, null)) {
            return new ResponseEntity<>("getSnippet", HttpStatus.NOT_FOUND);
        }
        if ((code.getViews() < 0 && code.isViewRestricted()) ||
                (code.getTime() < 0 && code.isTimeRestricted())) {
            return new ResponseEntity<>("getSnippet", HttpStatus.NOT_FOUND);
        }
        return "getSnippet";
    }

    @GetMapping("/code/new")
    public ResponseEntity<?> getNewCode() {
        return codeService.getNewWebCode();
    }

    @GetMapping("/code/latest")
    public String getLatestSnippets(Model model) {
        model.addAttribute("latest_list", codeService.getLatestList());
        return "latest";
    }


}
