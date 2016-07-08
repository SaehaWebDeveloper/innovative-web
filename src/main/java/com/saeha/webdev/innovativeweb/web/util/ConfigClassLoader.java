package com.saeha.webdev.innovativeweb.web.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration Class 로드 
 * 
 * @author Pure
 *
 */
@Slf4j
public class ConfigClassLoader {
	/**
	 * Context의 사용 위치
	 * @author Pure
	 *
	 */
	@AllArgsConstructor
	public enum ConfigType{
		/**
		 * Root Context
		 */
		ROOT("/config/root/**/*.class"), 
		/**
		 * Serlvet Context
		 */
		SERVLET("/config/servlet/**/*.class");
		
		/**
		 * Context 파일의 위치
		 */
		@Getter private String path;
	}
	
	/**
	 * Configuration Class load
	 * 
	 * @param configType
	 * @param basePackage
	 * @return
	 */
	public static Class<?>[] getConfigClass(ConfigType configType, String basePackage){
		List<Class<?>> resultClassList = new ArrayList<>();
		try{
			basePackage = basePackage.replace(".", "/");
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources(basePackage + configType.getPath());
			
			for(Resource resource : resources){
				MetadataReader metadataReader = new SimpleMetadataReaderFactory().getMetadataReader(resource);
				String className = metadataReader.getClassMetadata().getClassName();
				if(metadataReader.getAnnotationMetadata().hasAnnotation(Configuration.class.getName())){
					log.info("LoadType:{}, Class:{}", configType, className);
					resultClassList.add(Class.forName(className));
				}
			}
		}catch(Exception e){
			log.error("ConfigClass Load Error.", e);
		}
		log.debug("resultClassList:{}", resultClassList);
		return resultClassList.toArray(new Class<?>[resultClassList.size()]);
	}
}
