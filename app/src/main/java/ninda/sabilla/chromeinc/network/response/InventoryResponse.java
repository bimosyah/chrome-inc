package ninda.sabilla.chromeinc.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ninda.sabilla.chromeinc.network.entity.Inventory;

public class InventoryResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("inventory")
    private List<Inventory> inventory = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }

}
