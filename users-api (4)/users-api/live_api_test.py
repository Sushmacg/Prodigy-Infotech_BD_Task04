import http.client
import json
import time
import uuid

HOST = 'localhost'
PORT = 8020


def request(method, path, body=None, headers=None):
    conn = http.client.HTTPConnection(HOST, PORT, timeout=10)
    hdrs = {} if headers is None else dict(headers)
    if body is not None:
        if not isinstance(body, (str, bytes)):
            body = json.dumps(body)
        hdrs.setdefault('Content-Type', 'application/json')
    conn.request(method, path, body, hdrs)
    res = conn.getresponse()
    text = res.read().decode('utf-8')
    try:
        data = json.loads(text)
    except Exception:
        data = text
    return res.status, data

print('admin login')
status, data = request('POST', '/api/auth/login', {'email': 'admin@example.com', 'password': 'ChangeThisPassword123'})
print(status, data)
if status != 200:
    raise SystemExit('admin login failed')
admin_token = data['token']

unique = str(uuid.uuid4())
user_email = f'user_{unique}@example.com'
print('register user', user_email)
status, data = request('POST', '/api/auth/register', {'name': 'NewUser', 'email': user_email, 'password': 'password123', 'age': 25})
print(status, data)
if status != 201:
    raise SystemExit('register failed')
user_token = data['token']

print('profile get')
status, data = request('GET', '/api/profile', headers={'Authorization': 'Bearer ' + user_token})
print(status, data)
if status != 200:
    raise SystemExit('profile get failed')

print('profile update')
status, data = request('PUT', '/api/profile', {'name': 'UpdatedUser'}, headers={'Authorization': 'Bearer ' + user_token})
print(status, data)
if status != 200:
    raise SystemExit('profile update failed')

print('admin list users')
status, data = request('GET', '/api/users', headers={'Authorization': 'Bearer ' + admin_token})
print(status, data if isinstance(data, list) else str(data)[:400])
if status != 200:
    raise SystemExit('admin list users failed')

print('admin create user')
new_user_email = f'admin_test_{unique}@example.com'
status, data = request('POST', '/api/users', {'name': 'AdminCreated', 'email': new_user_email, 'password': 'password123', 'age': 40, 'role': 'USER'}, headers={'Authorization': 'Bearer ' + admin_token})
print(status, data)
if status != 201:
    raise SystemExit('admin create failed')
user_id = data['id']

print('admin get new user')
status, data = request('GET', f'/api/users/{user_id}', headers={'Authorization': 'Bearer ' + admin_token})
print(status, data)
if status != 200:
    raise SystemExit('admin get new user failed')

print('admin update new user')
status, data = request('PUT', f'/api/users/{user_id}', {'name': 'AdminUpdated', 'role': 'ADMIN'}, headers={'Authorization': 'Bearer ' + admin_token})
print(status, data)
if status != 200:
    raise SystemExit('admin update failed')

print('admin delete new user')
status, data = request('DELETE', f'/api/users/{user_id}', headers={'Authorization': 'Bearer ' + admin_token})
print(status, data)
if status != 204:
    raise SystemExit('admin delete failed')

print('admin get deleted user')
status, data = request('GET', f'/api/users/{user_id}', headers={'Authorization': 'Bearer ' + admin_token})
print(status, data)
if status != 404:
    raise SystemExit('get deleted user failed')

print('user forbidden access admin list')
status, data = request('GET', '/api/users', headers={'Authorization': 'Bearer ' + user_token})
print(status, data)
if status != 403:
    raise SystemExit('user forbidden access failed')

print('ALL_OK')
