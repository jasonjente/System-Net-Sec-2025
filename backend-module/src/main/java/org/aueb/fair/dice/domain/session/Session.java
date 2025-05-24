package org.aueb.fair.dice.domain.session;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    // session id
    private Long id;
    private String notes;

    //TODO Start adding fields for session, pass them along to entity and DTO use the same names in the variables.

    // user id - + JWT token
    // choice manas
    // choice paixti
    // timestamp
    // winner (tentative?)
}
