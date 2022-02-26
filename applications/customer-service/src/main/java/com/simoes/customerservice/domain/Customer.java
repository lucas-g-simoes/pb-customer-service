package com.simoes.customerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pb_customer")
@Where(clause = "CUSTOMER_ACTIVE = true")
public class Customer {

	@Id
	@Column(name = "CUSTOMER_ID", length = 36)
	private String id;

	@Column(name = "CUSTOMER_NAME", nullable = false)
	private String name;

	@Column(name = "CUSTOMER_CODE", nullable = false, length = 14)
	private String code;

	@Column(name = "CUSTOMER_BIRTHDATE", nullable = false)
	private LocalDate birthdate;

	@Column(name = "CUSTOMER_ACTIVE", nullable = false)
	private Boolean active = true;

	@PrePersist
	public void onPrePersist() {
		this.setId(UUID.randomUUID().toString());
	}

}
