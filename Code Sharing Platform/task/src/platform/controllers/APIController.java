package platform.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import platform.Code;
import platform.CodeRepository;
import platform.CodeService;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api")
public class APIController {

    @Autowired
    private CodeService codeService;

    @GetMapping("/code/{id}")
    public ResponseEntity<?> getCodeSnippet(@PathVariable UUID id) {
        Code code = codeService.getCodeSnippet(id);

        if (Objects.equals(code, null) || (code.getViews() <= 0 && code.isViewRestricted()) ||
                (code.getTime() <= 0 && code.isTimeRestricted())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(code);
    }

    @PostMapping("/code/new")
    public ResponseEntity<?> postCodeSnippet(@RequestBody Code code) {
        return ResponseEntity.ok(codeService.postCodeSnippet(code));
    }

    @GetMapping("/code/latest")
    public ResponseEntity<?> getLatestSnippets() {
        return ResponseEntity.ok(codeService.getLatestList());
    }

}
