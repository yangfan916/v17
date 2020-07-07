package com.yangfan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.TreeSet;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem implements Serializable,Comparable<CartItem> {


    private static final long serialVersionUID = -5325941589283507954L;
    private Long productId;

    private Integer count;

    private Date updateTime;

    public int compareTo(CartItem o) {
        return (int) (o.getUpdateTime().getTime() - this.getUpdateTime().getTime());
    }

    /*public static void main(String[] args) {
        TreeSet<CartItem> treeSet = new TreeSet<CartItem>();
        treeSet.add(new CartItem(1L, 100, new Date()));
        treeSet.add(new CartItem(3L, 300, new Date(System.currentTimeMillis()+200)));
        treeSet.add(new CartItem(2L, 200, new Date(System.currentTimeMillis()+100)));

        for(CartItem cartItem : treeSet){
            System.out.println(cartItem);
        }

    }*/
}
