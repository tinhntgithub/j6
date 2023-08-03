package j6.asm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReCapchaResponse {
    private boolean success;
    private String challenge_ts;
    private String hostname;
}
