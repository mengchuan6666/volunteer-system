import request from '@/utils/request'

// 查询志愿者信息列表
export function listVolunteer(query) {
  return request({
    url: '/system/volunteer/list',
    method: 'get',
    params: query
  })
}

// 查询志愿者信息详细
export function getVolunteer(id) {
  return request({
    url: '/system/volunteer/' + id,
    method: 'get'
  })
}

// 新增志愿者信息
export function addVolunteer(data) {
  return request({
    url: '/system/volunteer',
    method: 'post',
    data: data
  })
}

// 修改志愿者信息
export function updateVolunteer(data) {
  return request({
    url: '/system/volunteer',
    method: 'put',
    data: data
  })
}

// 删除志愿者信息
export function delVolunteer(id) {
  return request({
    url: '/system/volunteer/' + id,
    method: 'delete'
  })
}
