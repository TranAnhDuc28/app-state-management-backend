package com.demo.api.entity;

import com.demo.api.enumeration.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servers")
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_address", unique = true)
    @NotBlank(message = "IP Address can not be empty or null")
    private String ipAddress;

    @Column(name = "name")
    private String name;

    @Column(name = "memory")
    private String memory;

    @Column(name = "type")
    private String type;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
