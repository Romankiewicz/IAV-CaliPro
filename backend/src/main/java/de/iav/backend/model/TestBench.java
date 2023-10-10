package de.iav.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "testBench")
public record TestBench(
        @MongoId
        String benchId,
        String name,
        @DBRef
        @JsonIgnoreProperties("testBench")
        List<Metrology> metrology,
        @DBRef(lazy = true)
        @JsonIgnoreProperties("testBench")
        @JsonManagedReference
        List<Operator> operator,
        LocalDate maintenance,
        LocalDate calibration
) {
//
//        public TestBench withOperator(List<Operator> operators) {
//                return new TestBench(
//                        this.benchId,
//                        this.name,
//                        this.metrology,
//                        operators,
//                        this.maintenance,
//                        this.calibration);
//        }
}
