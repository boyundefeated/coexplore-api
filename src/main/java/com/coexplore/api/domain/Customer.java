package com.coexplore.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "jhi_level")
    private String level;

    @OneToMany(mappedBy = "customer")
    private Set<Reservation> reservations = new HashSet<>();
    @OneToMany(mappedBy = "customer")
    private Set<Purchase> purchases = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public Customer userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public Customer password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public Customer level(String level) {
        this.level = level;
        return this;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Customer reservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public Customer addReservations(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setCustomer(this);
        return this;
    }

    public Customer removeReservations(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setCustomer(null);
        return this;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public Customer purchases(Set<Purchase> purchases) {
        this.purchases = purchases;
        return this;
    }

    public Customer addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
        purchase.setCustomer(this);
        return this;
    }

    public Customer removePurchase(Purchase purchase) {
        this.purchases.remove(purchase);
        purchase.setCustomer(null);
        return this;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
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
        Customer customer = (Customer) o;
        if (customer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", level='" + getLevel() + "'" +
            "}";
    }
}
