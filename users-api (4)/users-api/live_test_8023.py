import http.client
import json
import uuid

HOST='localhost'
PORT=8023

def request(method, path, body=None, headers=None):
    conn = http.client.HTTPConnection(HOST, PORT, timeout=10)
    hdrs = {} if headers is None else dict(headers)
    payload = None
    if body is not None:
        if isinstance(body, str):
            payload = body.encode('utf-8')
        else:
            payload = json.dumps(body).encode('utf-8')
        hdrs['Content-Type'] = 'application/json'
    conn.request(method, path, payload, hdrs)
    resp = conn.getresponse()
    text = resp.read().decode('utf-8')
    try:
        data = json.loads(text)
    except Exception:
        data = text
    return resp.status, data

print('ping /')
status, data = request('GET', '/')
print(status, data)

print('admin login')
status, data = request('POST', '/api/auth/login', {'email': 'admin@example.com', 'password': 'ChangeThisPassword123'})
print(status, data)
if status != 200:
    raise SystemExit('admin login failed')
admin_token = data['token']

unique = uuid.uuid4().hex[:8]
user_email = f'live_test_{unique}@example.com'
print('register', user_email)
status, data = request('POST', '/api/auth/register', {'name': 'LiveUser', 'email': user_email, 'password': 'password123', 'age': 28})
print(status, data)
if status != 201:
    raise SystemExit('register failed')
user_token = data['token']

print('profile get')
status, data = request('GET', '/api/profile', headers={'Authorization': 'Bearer ' + user_token})
print(status, data)
if status != 200:
    raise SystemExit('profile get failed')

print('profile put')
status, data = request('PUT', '/api/profile', {'name': 'LiveUserUpdated'}, headers={'Authorization': 'Bearer ' + user_token})
print(status, data)
if status != 200:
    raise SystemExit('profile put failed')

print('admin list')
status, data = request('GET', '/api/users', headers={'Authorization': 'Bearer ' + admin_token})
print(status, type(data), len(data) if isinstance(data, list) else data)
if status != 200:
    raise SystemExit('admin list failed')

new_email = f'live_admin_{unique}@example.com'
print('admin create', new_email)
status, data = request('POST', '/api/users', {'name': 'LiveAdminUser', 'email': new_email, 'password': 'password123', 'age': 35, 'role': 'USER'}, headers={'Authorization': 'Bearer ' + admin_token})
print(status, data)
if status != 201:
    raise SystemExit('admin create failed')
created_id = data['id']

print('admin get created')
status, data = request('GET', f'/api/users/{created_id}', headers={'Authorization': 'Bearer ' + admin_token})
print(status, data)
if status != 200:
    raise SystemExit('admin get created failed')

print('admin update')
status, data = request('PUT', f'/api/users/{created_id}', {'name': 'LiveAdminUserUpdated', 'role': 'ADMIN'}, headers={'Authorization': 'Bearer ' + admin_token})
print(status, data)
if status != 200:
    raise SystemExit('admin update failed')

print('admin delete')
status, data = request('DELETE', f'/api/users/{created_id}', headers={'Authorization': 'Bearer ' + admin_token})
print(status, data)
if status != 204:
    raise SystemExit('admin delete failed')

print('admin get deleted')
status, data = request('GET', f'/api/users/{created_id}', headers={'Authorization': 'Bearer ' + admin_token})
print(status, data)
if status != 404:
    raise SystemExit('admin get deleted failed')

print('user forbidden admin list')
status, data = request('GET', '/api/users', headers={'Authorization': 'Bearer ' + user_token})
print(status, data)
if status != 403:
    raise SystemExit('user forbidden admin list failed')

print('ALL_OK')
