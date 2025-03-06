package com.salomovs95.event.generator.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
  @NotNull @NotBlank @Size(min=7) String username,
  @NotNull @NotBlank @Size(min=7) @Email String email
) {}
