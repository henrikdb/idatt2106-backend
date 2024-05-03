package no.ntnu.idi.stud.savingsapp.dto.store;

import lombok.Data;

@Data
public class ItemDTO {

    private long id;
    private String itemName;
    private int price;
    private long imageId;
    private boolean alreadyBought;

}
