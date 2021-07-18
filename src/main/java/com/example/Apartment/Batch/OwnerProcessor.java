package com.example.Apartment.Batch;

import com.example.Apartment.Entity.OwnerDetails;
import org.springframework.batch.item.ItemProcessor;


/**
 * @author arun vemireddy
 */

public class OwnerProcessor implements ItemProcessor<OwnerDetailsInput,OwnerDetails>{


    @Override
    public OwnerDetails process(final OwnerDetailsInput item) throws Exception {
        OwnerDetails ownerDetails= new OwnerDetails();
        ownerDetails.setId(Integer.parseInt(item.getId()));
        ownerDetails.setName(item.getName());
        ownerDetails.setContact(Long.parseLong(item.getContact()));
        ownerDetails.setFlatno(Integer.parseInt(item.getFlatno()));
        return ownerDetails;
    }
}
