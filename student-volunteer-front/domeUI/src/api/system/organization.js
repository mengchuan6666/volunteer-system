import request from '@/utils/request'

// 查询志愿组织信息列表
export function listOrganization(query) {
  return request({
    url: '/system/organization/list',
    method: 'get',
    params: query
  })
}

// 查询志愿组织信息详细
export function getOrganization(id) {
  return request({
    url: '/system/organization/' + id,
    method: 'get'
  })
}

// 新增志愿组织信息
export function addOrganization(data) {
  return request({
    url: '/system/organization',
    method: 'post',
    data: data
  })
}

// 修改志愿组织信息
export function updateOrganization(data) {
  return request({
    url: '/system/organization',
    method: 'put',
    data: data
  })
}

// 删除志愿组织信息
export function delOrganization(id) {
  return request({
    url: '/system/organization/' + id,
    method: 'delete'
  })
}
