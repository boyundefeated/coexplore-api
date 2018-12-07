package com.coexplore.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Purchase.
 */
@Entity
@Table(name = "purchase")
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "to_date")
    private Instant toDate;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JsonIgnoreProperties("purchases")
    private Customer customer;

    @OneToOne    @JoinColumn(unique = true)
    private Membership membership;

    @OneToOne    @JoinColumn(unique = true)
    private Promotion promotion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public Purchase fromDate(Instant fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public Purchase toDate(Instant toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Double getAmount() {
        return amount;
    }

    public Purchase amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Purchase customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Membership getMembership() {
        return membership;
    }

    public Purchase membership(Membership membership) {
        this.membership = membership;
        return this;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public Purchase promotion(Promotion promotion) {
        this.promotion = promotion;
        return this;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Purchase purchase = (Purchase) o;
        if (purchase.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchase.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Purchase{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
