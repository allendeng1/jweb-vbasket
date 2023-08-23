package com.jweb.service.file;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jweb.common.exception.FileException;
import com.jweb.common.exception.MyException;
import com.jweb.common.file.storage.FileBizType;
import com.jweb.common.file.storage.FileStorageProvider;
import com.jweb.common.file.storage.FileStorageResult;
import com.jweb.common.file.storage.FileStorageSite;
import com.jweb.common.util.DataUtil;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.dao.FileStorageDao;
import com.jweb.dao.base.BaseEntity.Sort;
import com.jweb.dao.entity.FileStorage;

@Service
public class FileServiceImpl extends DataUtil implements FileService {
	
	@Autowired(required = false)
	private FileStorageProvider[] storages;
	@Autowired
	private FileStorageDao fileStorageDao;
	
	@Value("${file.storage.site:}")
	private String storageSite;
	
	private FileStorageProvider fileStorage = null;

	@Override
	public FileStorageResult uploadFile(String owner, FileBizType type,  MultipartFile file, HttpServletRequest request) throws MyException {
		if(fileStorage == null) {
			FileException.fileStorageNotFoundError();
		}
		String domain = null;
		if(isNotNull(request)) {
			StringBuffer url = request.getRequestURL();
			domain = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
		}
		FileStorageResult result = fileStorage.save(type, owner, file, domain);
		if(storageSite.equals(FileStorageSite.MYSITE.name())) {
			return result;
		}
		if(type.isOnly()) {
			FileStorage query = new FileStorage();
			query.setOwner(owner);
			query.setType(type.getType());
			FileStorage oldFile = fileStorageDao.selectOneByExample(query);
			if(oldFile != null) {
				FileStorage newFile = new FileStorage();
				newFile.setId(oldFile.getId());
				newFile.setFileName(result.getFileName());
				newFile.setFileSize(result.getFileSize());
				newFile.setFileType(result.getFileType());
				newFile.setMddate(DateTimeUtil.nowTime());
				newFile.setStoragePath(result.getFilePath());
				newFile.setStorageSite(result.getStorageSite().name());
				fileStorageDao.updateById(newFile);
				return result;
			}
		}
		FileStorage fs = new FileStorage();
		fs.setCtdate(DateTimeUtil.nowTime());
		fs.setFileName(result.getFileName());
		fs.setFileSize(result.getFileSize());
		fs.setFileType(result.getFileType());
		fs.setIsDelete(false);
		fs.setMddate(DateTimeUtil.nowTime());
		fs.setOwner(owner);
		fs.setStoragePath(result.getFilePath());
		fs.setStorageSite(result.getStorageSite().name());
		fs.setType(result.getBizType().getType());
		fileStorageDao.insert(fs);
		
		return result;
	}
	
	@Override
	public List<String> getFileUrls(String owner, FileBizType type, HttpServletRequest request) throws MyException {
		FileStorage query = new FileStorage();
		query.setOwner(owner);
		query.setType(type.getType());
		query.sort("ctdate", Sort.ASC);
		List<FileStorage> files = fileStorageDao.selectByExample(query);
		if(isNullOrEmpty(files)) {
			return new ArrayList<>();
		}
		String domain = null;
		if(isNotNull(request)) {
			StringBuffer url = request.getRequestURL();
			domain = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
		}
		List<String> urls = new ArrayList<String>();
		for(FileStorage file : files) {
			String url = fileStorage.getFileUrl(file.getStoragePath(), file.getFileName(), domain);
			urls.add(url);
		}
		return urls;
	}
	
	@PostConstruct
	public void init() {
		if(isNullOrEmpty(storages)) {
			return;
		}
		for(FileStorageProvider storage : storages) {
			if(isEqualIgnoreCase(storage.storageSite().name(), storageSite)) {
				fileStorage = storage;
			}
		}
	}

}
