package sk.qbsw.core.security.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.security.model.domain.CGroup;

public interface IGroupService extends Serializable
{

	public abstract List<CGroup> getAll ();

	public abstract List<CGroup> getByCode (String code);

}
