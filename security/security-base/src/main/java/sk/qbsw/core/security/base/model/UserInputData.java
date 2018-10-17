package sk.qbsw.core.security.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static sk.qbsw.core.base.validation.ValidationGroup.Update;

/**
 * The user input data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInputData implements Serializable
{
	private static final long serialVersionUID = -9103874418613247759L;

	@NotNull (groups = {Update.class})
	private Long id;
}
