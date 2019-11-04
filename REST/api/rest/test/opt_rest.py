import json, requests, sys, os.path
import rest_conf as conf
import time

def get_opt_list():
    url = conf.BT_SERVER + "/opt/list"        
    headers = {}
    return requests.get(url, headers=headers, verify=False, stream=True)

def find_opt(name):
    resp = get_opt_list()
    # TBD: check resp.status_code here
    lst = resp.json()
    return name.upper() in lst


def run_opt(id, file):

    url = conf.BT_SERVER + "/opt/run"
    fname = conf.DATA_PATH + file

    headers = {}
    with open(fname, "rb") as f:
        params = dict (
            name = id,
            src = (fname, f),
        )


        t0 = time.time()
        resp = requests.post(url, headers=headers, files=params, verify=False)
        if resp.status_code != 200: 
            raise Exception("Invalid response code", resp.status_code, resp.content)
        t1 = time.time()
        print("Optimizing done in", (t1 - t0), "seconds")
        return resp.content

def del_opt(id):
    url = conf.BT_SERVER + "/opt/" + id + "/delete"
    resp = requests.delete(url, verify=False)
    if resp.status_code != 200 and resp.status_code != 204:
        raise Exception("Invalid response code", resp.status_code, resp.content)
    return resp


def get_file(url, out):
    resp = requests.get(url, verify=False, stream=True)

    if resp.status_code != 200: 
        raise Exception("Invalid response code", resp.status_code, resp.content)

    with open(out, 'wb') as fd:
        for chunk in resp.iter_content(1024):
            fd.write(chunk)
    return True


def get_log(id, out):
    url = conf.BT_SERVER + "/opt/" + id + "/log"
    return get_file(url, out)

def get_input(id, out):
    url = conf.BT_SERVER + "/opt/" + id + "/input"
    return get_file(url, out)

def get_output(id, out):
    url = conf.BT_SERVER + "/opt/" + id + "/output"
    get_file(url, out)

if __name__ == "__main__":
    r = get_opt_list()
    js = r.json()
    for b in js:
        print (b)
