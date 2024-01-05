package com.example.Apartment.Batch;

import com.example.Apartment.Dao.OwnerDetailsDao;
import com.example.Apartment.Entity.OwnerDetails;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author arun vemireddy
 */
@Configuration
public class DBWriter implements ItemWriter<OwnerDetails> {

	@Autowired
	private OwnerDetailsDao ownerDetailsDao;

	@Override
	public void write(List<? extends OwnerDetails> items) throws Exception {
		ownerDetailsDao.saveAll(items);
	}
}
