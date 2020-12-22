package br.com.itix.comissoescnu.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.itix.xdk.job.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode( callSuper = false )
public class LoadLegacyMessage<T> extends BaseMessage {

	private static final long serialVersionUID = 8881059298570993238L;

	private String message;

	private List<T> legacy = new ArrayList<T>();

}
