package org.example.tltravel.view.out;

public class RoleOutView {
    private Long id;
    private String name;


    public RoleOutView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleOutView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
