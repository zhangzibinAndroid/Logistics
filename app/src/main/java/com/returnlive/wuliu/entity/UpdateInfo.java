package com.returnlive.wuliu.entity;

/**
 * @author 张梓彬
 * Data : 2017/5/18 0018
 * Time : 下午 6:15
 * Describe : 获取版本更新信息实体类封装
 */
public class UpdateInfo
{
        private String version;
        private String description;
        private String url;
        
        public String getVersion()
        {
                return version;
        }
        public void setVersion(String version)
        {
                this.version = version;
        }
        public String getDescription()
        {
                return description;
        }
        public void setDescription(String description)
        {
                this.description = description;
        }
        public String getUrl()
        {
                return url;
        }
        public void setUrl(String url)
        {
                this.url = url;
        }
        
}
