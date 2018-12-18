/**
 *
 */
package com.risepu.ftk.chain.api;

/**
 * @author q-wang
 */
public interface ChainService {

	public String sign(Long documentId);

	public boolean verify(Long documentId);

	public Long query(String hash, String corpId, String userId);

}
