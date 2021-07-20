package com.example.Apartment.Batch;

import com.example.Apartment.Entity.OwnerDetails;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


/**
 * @author arun vemireddy
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final String[] FIELD_NAMES = new String[]{"id","name","contact","flatno"};

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<OwnerDetailsInput> reader() {
        return new FlatFileItemReaderBuilder<OwnerDetailsInput>()
                .name("matchItemReader")
                .resource(new ClassPathResource("data.csv"))
                .delimited()
                .names(FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<OwnerDetailsInput>() {{
                    setTargetType(OwnerDetailsInput.class);
                }})
                .build();
    }

    public OwnerProcessor ownerProcessor(){
        return new OwnerProcessor();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener,Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemWriter<OwnerDetails> writer) {
        return stepBuilderFactory.get("step1")
                .<OwnerDetailsInput, OwnerDetails> chunk(10)
                .reader(reader())
                .processor(ownerProcessor())
                .writer(writer)
                .build();
    }
}
