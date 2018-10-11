package sk.qbsw.core.security.base.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The account data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.2
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountData implements Serializable
{
	private static final long serialVersionUID = -4837593660905770L;

	@NotNull
	private Long id;

	@NotNull
	private String uid;

	@NotNull
	private String login;

	private String email;

	@NotNull
	private AccountDataTypes type;

	@NotNull
	private List<String> groups;

	@NotNull
	private List<String> roles;

	private UserOutputData user;

	@NotNull
	private Map<String, Object> additionalInformation = new HashMap<>();
}
