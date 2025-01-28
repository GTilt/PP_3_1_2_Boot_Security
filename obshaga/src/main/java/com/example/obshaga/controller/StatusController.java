package com.example.obshaga.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class StatusController {
//
//    @GetMapping("/presence")
//    @PreAuthorize("hasAnyRole('GUARD', 'COMMANDANT')")
//    public ResponseEntity<Map<String, Object>> getPresenceStats() {
//        long present = studentService.countPresent();
//        long total = studentService.countAll();
//        return ResponseEntity.ok(Map.of(
//                "present", present,
//                "absent", total - present
//        ));
//    }
}