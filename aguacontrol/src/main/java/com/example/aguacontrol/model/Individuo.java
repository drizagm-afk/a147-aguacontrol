package com.example.aguacontrol.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Entity
@Table(name = "INDIVIDUOS")
@PrimaryKeyJoinColumn(name = "id_persona")
public class Individuo extends Persona {
}
