package sk.qbsw.security.spring.base.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * The simplified organization.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Organization implements Serializable
{
	private static final long serialVersionUID = 3528029332180443692L;

	@NotNull
	private Long id;

	private String name;

	private String code;
}
