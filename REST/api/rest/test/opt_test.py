import json, requests, sys, os.path, os
import rest_conf as conf
import opt_rest as opt
import unittest
import time


class OptTest(unittest.TestCase):

    def run_opt(self, name, proj):
        try:
            resp = opt.run_opt(name, proj)
            rc = opt.find_opt(name)
            self.assertTrue(rc, "New opt {0} not found".format(name))

            os.makedirs(conf.OUT_PATH, exist_ok = True)
            
            input_file = conf.OUT_PATH + name + ".input"
            resp = opt.get_input(name, input_file)
            self.check_file(input_file)
            
            out_file = conf.OUT_PATH + name + ".out"
            resp = opt.get_output(name, out_file)
            self.check_file(out_file)
            
            log_file = conf.OUT_PATH + name + ".log"
            resp = opt.get_log(name, log_file)
            self.check_file(log_file)

            resp = opt.del_opt(name)    
            rc = opt.find_opt(name)
            self.assertTrue(not rc, "Deleted opt {0} is still here".format(name))

        except Exception as e:
            self.fail(str(e))

    def test_simple_opt(self):
        name = conf.OPT_NAME  + "-" + str(int(time.time()))
        proj = conf.OPT_PROJ
        self.run_opt(name, proj)
    
    
    def check_file(self, path):
        ok = os.path.getsize(path) > 0
        self.assertTrue(ok, "Empty file {0}".format(path))
        os.remove(path)

    
if __name__ == "__main__":
    unittest.main()
