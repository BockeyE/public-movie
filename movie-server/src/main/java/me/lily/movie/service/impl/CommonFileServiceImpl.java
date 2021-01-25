package me.lily.movie.service.impl;

import me.lily.movie.domain.CommonFile;
import me.lily.movie.repository.CommonFileRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommonFileServiceImpl {

    @Resource
    CommonFileRepository fileDao;

    public void insertOne(String origName, String filurl, String hash) {
        CommonFile f = new CommonFile();
        f.setFileUrl(filurl);
        f.setFileName(origName);
        f.setFileHash(hash);
        CommonFile commonFile = fileDao.findByFileHash(hash);
        if (commonFile == null) {
            fileDao.save(f);
        }
    }

    public CommonFile getByHash(String hash) {
        return fileDao.findByFileHash(hash);
    }

    public int deleteByHash(String hash) {
        return fileDao.deleteByFileHash(hash);
    }
}
