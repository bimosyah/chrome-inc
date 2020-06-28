package ninda.niar.chromeinc.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ninda.niar.chromeinc.network.entity.Customer;

public class CustomerResponse {

    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data_customer")
    private List<Customer> dataCustomer = null;

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

    public List<Customer> getDataCustomer() {
        return dataCustomer;
    }

    public void setDataCustomer(List<Customer> dataCustomer) {
        this.dataCustomer = dataCustomer;
    }
}
