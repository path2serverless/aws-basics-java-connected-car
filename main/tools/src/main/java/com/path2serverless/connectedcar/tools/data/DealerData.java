package com.path2serverless.connectedcar.tools.data;

import com.opencsv.bean.CsvBindByPosition;
import com.path2serverless.connectedcar.shared.data.attributes.Address;
import com.path2serverless.connectedcar.shared.data.entities.Dealer;
import com.path2serverless.connectedcar.shared.data.enums.StateCodeEnum;

public class DealerData {

  @CsvBindByPosition(position = 0)
  private String name = null;

  @CsvBindByPosition(position = 1)
  private String streetAddress = null;

  @CsvBindByPosition(position = 2)
  private String city = null;

  @CsvBindByPosition(position = 3)
  private String state = null;

  @CsvBindByPosition(position = 4)
  private String zipCode = null;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public Dealer GetDealer()
  {
    Dealer dealer = new Dealer();

    dealer.setName(name);
    dealer.setAddress(new Address());
    dealer.getAddress().setStreetAddress(streetAddress);
    dealer.getAddress().setCity(city);
    dealer.getAddress().setState(state);
    dealer.getAddress().setZipCode(zipCode);
    dealer.setStateCode(StateCodeEnum.valueOf(state));

    return dealer;
  }
}
