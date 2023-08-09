package j6.asm.controller.user;

import j6.asm.entity.Favorites;

public class FavoritesResponse {
    private Favorites favorites;
    private String productColor;

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }
}