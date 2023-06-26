package assogym.commun;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import assogym.data.Compte;
import assogym.data.Evenement;
import assogym.data.Parti;
import assogym.data.Salle;
import assogym.data.Utilisateur;


@Mapper
public interface IMapper {
	
	@Mapping( target="id_salle", expression="java( evenement.getId_salle() )" )
	Evenement update( @MappingTarget Evenement draft, Evenement evenement  );
	Utilisateur update( @MappingTarget Utilisateur target, Utilisateur source  );
	Compte update( @MappingTarget Compte draft, Compte compte  );
	Salle update(@MappingTarget Salle draft, Salle salle);
	
	//@Mapping( target="memo", expression="java( source.getMemo() )" )
	Parti update( @MappingTarget Parti draft, Parti compte  );
	
}
