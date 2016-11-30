package org.datasays.wes.benchmark.impl;

import org.datasays.util.JsonObjGetter;
import org.datasays.util.http.HttpClientBuilder;
import org.datasays.wes.EsHelper2;
import org.datasays.wes.benchmark.IESClient;
import org.datasays.wes.toolkit.WGsonConvert;
import org.datasays.wes.vo.IEsItem;
import org.datasays.wes.vo.Query;
import org.datasays.wes.vo.SearchQuery;
import org.datasays.wes.vo.WSearchResult;

/**
 * Created by watano on 2016/11/28.
 */
public class WESClient implements IESClient {
		private EsHelper2 helper = null;

		@Override
		public void init() throws Exception {
				String serverUrl = "http://127.0.0.1:9200/";
				helper = new EsHelper2(serverUrl, null, null);
				HttpClientBuilder cbulder = new HttpClientBuilder();
				helper.init(cbulder.build(), new WGsonConvert(), false);
		}

		@Override
		public void close() throws Exception {
		}

		@Override
		public boolean createIndex(String index) throws Exception {
				Object result =  helper.createIndex(index, 3,3);
				if(result != null){
						return true;
				}
				return false;
		}

		@Override
		public boolean delIndex(String index) throws Exception {
				return helper.delIndex(index);
		}

		@Override
		public IEsItem saveIndex(IEsItem doc) throws Exception {
				return helper.save(doc);
		}

		@Override
		public <T> WSearchResult<T> searchObj(String index, String type, SearchQuery query, Class<T> cls) throws Exception {
				return helper.searchObj(index, type, query, cls);
		}

		@Override
		public IEsItem get(IEsItem doc) throws Exception {
				return helper.get(doc);
		}

		@Override
		public boolean delete(IEsItem doc) throws Exception {
				return helper.delete(doc);
		}

		@Override
		public boolean deleteByQuery(String index, String type, Query query) throws Exception {
				JsonObjGetter result = new JsonObjGetter(helper.deleteByQuery(index, type, query));
				if(result.bool("acknowledged")){
					return true;
				}
				return false;
		}
}