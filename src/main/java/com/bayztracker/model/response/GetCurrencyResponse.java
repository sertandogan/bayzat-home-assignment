package com.bayztracker.model.response;

import com.bayztracker.domain.Currency;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCurrencyResponse {

    private long id;
    private String name;
    private String symbol;
    private Float currentPrice;
    private Date createdAt;

    public static GetCurrencyResponse create(Currency currency) {
        return new GetCurrencyResponse(
                currency.getId(),
                currency.getName(),
                currency.getSymbol(),
                currency.getCurrentPrice(),
                currency.getCreatedAt()
        );
    }

}
