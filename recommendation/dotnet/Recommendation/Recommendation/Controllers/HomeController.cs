using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

namespace Recommendation.Controllers
{
    [Route("/")]
    [ApiController]
    public class HomeController : ControllerBase
    {
        private const string RESPONSE_STRING_FORMAT = "recommendation v2 from {0}: {1}";
        private readonly string HOSTNAME = ParseContainerIdFromHostname(Environment.GetEnvironmentVariable("HOSTNAME") ?? "unknown"); 

        private static string ParseContainerIdFromHostname(string hostname)
        {
            hostname = hostname.Replace("recommendation-v1", "");
            hostname = hostname.Replace("recommendation-v2", "");

            return hostname;
        }

        private static int count = 0;
        private static bool timeout = false;
        private static bool misbehave = false;

        [HttpGet]
        public ActionResult<string> Get([FromHeader(Name = "user-agent")] string userAgent)
        {
            Console.WriteLine($"recommendation request from { HOSTNAME }: { count }");

            if (misbehave)
            {
                count = 0;
                Console.WriteLine("Misbehaving {0}", count);
                Response.StatusCode = 503;

                return string.Format($"recommendation misbehavior from { HOSTNAME }\n");
            }
            else
            {
                count++;
                if (timeout)
                {
                    Thread.Sleep(3000);
                }
                return String.Format(RESPONSE_STRING_FORMAT, HOSTNAME, count);
            }
        }

        [HttpGet("timeout")]
        public ActionResult<string> GetTimeout()
        {
            return "Following requests to '/' will time out.\n";
        }

        [HttpGet("notimeout")]
        public ActionResult<string> GetNoTimeout()
        {
            return "Following requests to '/' will not time out.\n";
        }

        [HttpGet("misbehave")]
        public ActionResult<string> GetMisbehave()
        {
            return "Following requests to '/' will return a 503.\n";
        }

        [HttpGet("behave")]
        public ActionResult<string> GetBehave()
        {
            return "Following requests to '/' will not return a 503.\n";
        }
    }
}
