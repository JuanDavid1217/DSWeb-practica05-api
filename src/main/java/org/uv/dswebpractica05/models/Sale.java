/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author David
 */
@Entity
@Table(name="sales")
public class Sale implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sale_id_seq")
    @SequenceGenerator(name="sale_id_seq", sequenceName="sale_id_seq", initialValue=1, allocationSize=1)
    @Column(name="id")
    private long id;
    @Column(name="date")
    private Date date;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User1 user;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="client_id")
    private Client client;
    @OneToMany(mappedBy="sale", cascade={CascadeType.ALL}, fetch=FetchType.LAZY, orphanRemoval = true)
    private List<Detail> details;
    @Column(name="total")
    private BigDecimal total;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User1 getUser() {
        return user;
    }

    public void setUser(User1 user) {
        this.user = user;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    
}
