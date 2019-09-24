package com.white.stratego.stratego.game;

import com.white.stratego.stratego.market.model.User;

import javax.persistence.*;

@Entity
public class Statistics {

    @OneToOne
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int win;

    private int loss;

    private int unfinished;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getUnfinished() {
        return unfinished;
    }

    public void setUnfinished(int unfinished) {
        this.unfinished = unfinished;
    }
}
