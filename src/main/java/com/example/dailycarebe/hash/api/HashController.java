package com.example.dailycarebe.hash.api;

import com.example.dailycarebe.base.BaseComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Profile("!prod")
public class HashController extends BaseComponent {

  @GetMapping("/action/convert-id")
  public ResponseEntity<String> getHash(long id) {
    return ResponseEntity.ok(convertToIdHash(id));
  }

  @GetMapping("/action/convert-hash")
  public ResponseEntity<Long> getId(String hash) {
    return ResponseEntity.ok(convertToId(hash));
  }
}
