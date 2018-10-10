package sk.qbsw.security.spring.iam.auth.firebase.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import sk.qbsw.security.spring.iam.auth.base.model.IAMAuthLoggedUser;

/**
 * The IAM auth user details checker.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class IAMAuthUserDetailsChecker extends AccountStatusUserDetailsChecker
{
	@Override
	public void check (UserDetails user)
	{
		super.check(user);

		try
		{
			FirebaseAuth.getInstance().verifyIdToken( ((IAMAuthLoggedUser) user).getToken(), true);
		}
		catch (FirebaseAuthException e)
		{
			throw new CredentialsExpiredException(messages.getMessage("AccountStatusUserDetailsChecker.credentialsExpired", "User credentials in Firebase have expired"));
		}
	}
}
