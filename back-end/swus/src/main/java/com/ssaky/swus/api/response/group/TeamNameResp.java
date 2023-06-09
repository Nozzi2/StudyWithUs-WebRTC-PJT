package com.ssaky.swus.api.response.group;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TeamNameResp {
    private String teamName;
}
