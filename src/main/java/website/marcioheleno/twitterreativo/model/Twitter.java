package website.marcioheleno.twitterreativo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document( collection = "twitter")
public class Twitter {

    @Id
    @Generated
    private String id;

    @NotBlank
    @Size(max = 140)
    private String titulo;

    @NonNull
    private String texto;

    @NotNull
    private Date dataCriacao;

    @NonNull
    private String usuario;

    @NonNull
    private Boolean status;


}
