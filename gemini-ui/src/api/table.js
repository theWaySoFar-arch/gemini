import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/vue-admin-template/table/list',
    method: 'get',
    params
  })
}

/**
 * 
 * // 导入axios或其他HTTP请求库
import axios from 'axios';

// 在handleSearch方法中发送请求
handleSearch() {
  const params = {
    title: this.searchTitle,
    author: this.searchAuthor,
    startDate: this.searchDate ? this.searchDate[0] : null,
    endDate: this.searchDate ? this.searchDate[1] : null
  };

  axios.get('/your-backend-api-endpoint', { params })
    .then(response => {
      // 处理从后端获取的数据
      this.list = response.data.items;
      this.total = response.data.total;
      this.currentPageData = response.data.items; // 或根据需要处理数据
    })
    .catch(error => {
      console.error('Error fetching data:', error);
    });
}
 */