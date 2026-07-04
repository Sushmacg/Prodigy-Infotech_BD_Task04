import http.client
import json
import time

HOST='localhost'
PORT=8022

def request(method, path, body=None, headers=None):
    conn = http.client.HTTPConnection(HOST, PORT, timeout=10)
    hdrs = {} if headers is None else dict(headers)
    if body is not None:
        if isinstance(body, str):
            payload = body.encode('utf-8')
        else:
            payload = json.dumps(body).encode('utf-8')
        hdrs['Content-Type'] = 'application/json'
        hdrs['Content-Length'] = str(len(payload))
    else:
        payload = None
    conn.request(method, path, payload, hdrs)
    res = conn.getresponse()
    text = res.read().decode('utf-8')
    try:
        data = json.loads(text)
    except Exception:
        data = text
    print('REQ', method, path, body, hdrs)
    print('RESP', res.status, data)
    return res.status, data

status, data = request('POST', '/api/auth/login', {'email': 'admin@example.com', 'password': 'ChangeThisPassword123'})
print(status, data)
if status != 200:
    raise SystemExit('LOGIN FAIL')
admin_token = data['token']
status, data = request('POST', '/api/users', {'name': 'TempUserP', 'email': 'tempuserp@example.com', 'password': 'password123', 'age': 30, 'role': 'USER'}, headers={'Authorization': 'Bearer ' + admin_token})
print(status, data)
if status != 201:
    raise SystemExit('CREATE FAIL')
user_id = data['id']

for body in [{'name': 'X'}, {'role': 'ADMIN'}, {'name': 'X', 'role': 'ADMIN'}]:
    status, data = request('PUT', f'/api/users/{user_id}', body, headers={'Authorization': 'Bearer ' + admin_token})
    print('UPDATE', body, status, data)

status, data = request('PUT', f'/api/users/{user_id}', '{"name":"X","role":"ADMIN"}', headers={'Authorization': 'Bearer ' + admin_token})
print('UPDATE_RAW_STRING', status, data)
