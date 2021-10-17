package com.agro.pojo.dto;

public class RoleDto {

    private String name;

    private String description;

    private String idList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", idList=" + idList +
                '}';
    }
}
