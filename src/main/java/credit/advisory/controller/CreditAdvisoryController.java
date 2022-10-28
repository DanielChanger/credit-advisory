package credit.advisory.controller;

import credit.advisory.model.Application;
import credit.advisory.service.CreditAdvisoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advisors")
@RequiredArgsConstructor
public class CreditAdvisoryController {

    private final CreditAdvisoryService creditAdvisoryService;

    @PostMapping("{id}/assign")
    public Application assignApplicationToAdvisory(@PathVariable("id") Long id) {
        return creditAdvisoryService.assignApplication(id);
    }
}
